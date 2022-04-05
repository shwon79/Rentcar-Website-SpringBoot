google.charts.load('current', {'packages':['corechart']});
let viewId = '';

function getViewId() {
    $.ajax({
        type: "GET",
        url: `/admin/ga/VIEW_ID`,
        dataType: "json",
        cache: false,
        success: function(data){
            viewId = data.VIEW_ID;
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            console.log("Status: " + textStatus);
        }
    });
};

let res = [];

function displayGraph(period = 'daily') {
    res = [];

    if (period === 'daily') {
        fetchingDailyGAData();
    } else if (period === 'weekly') {
        fetchingWeeklyGAData();
    } else if (period === 'monthly') {
        fetchingMonthlyGAData();
    }
};

// function displayWeeklyGraph() {
//     res = [];
//
//     fetchingWeeklyGAData();
// };
//
// function displayMonthlyGraph() {
//     res = [];
//
//     fetchingMonthlyGAData();
// };

function fetchingDailyGAData() {
    calculateDailyDateAndFetchingData(0);
    calculateDailyDateAndFetchingData(-1);
    calculateDailyDateAndFetchingData(-2);
    calculateDailyDateAndFetchingData(-3);
    calculateDailyDateAndFetchingData(-4);
    calculateDailyDateAndFetchingData(-5);
    calculateDailyDateAndFetchingData(-6);

    setTimeout(function() {
        sortList('daily', res);
    },1800)
};

function calculateDailyDateAndFetchingData(period) {
    let today = new Date();
    let startDate = today;
    let year, month, date;

    let oneDay = 1000 * 60 * 60 * 24 * 1 * period;

    startDate = new Date(startDate.getTime() + oneDay);

    year = startDate.getFullYear();
    month = startDate.getMonth() + 1;
    date = startDate.getDate();

    if (month.toString().length === 1) {
        month = '0' + month.toString();
    };
    if (date.toString().length === 1) {
        date = '0' + date.toString();
    }

    startDate = `${year}-${month}-${date}`;

    queryReports('daily', startDate, startDate);
}

function sortList(period, res) {
    let sortedList = [];

    let newList = [];
    for (let i = 0; i < res.length; i++) {
        let dateList = res[i][0].split('-');
        newList.push([new Date(dateList[0], dateList[1]-1, dateList[2]), res[i][1]]);

        sortedList = newList.sort(function(a,b){
            return new Date(a[0]) - new Date(b[0]);
        });
    }

    if (period === 'monthly') {
        for (let i = 0; i < sortedList.length; i++) {
            let year = new Date(sortedList[i][0]).getFullYear();
            let month = new Date(sortedList[i][0]).getMonth() + 1;
            sortedList[i][0] = `${year}년 ${month}월`;
        }
    } else if (period === 'weekly') {
        for (let i = 0; i < sortedList.length; i++) {
            let month = new Date(sortedList[i][0]).getMonth() + 1;
            let day = new Date(sortedList[i][0]).getDate();
            sortedList[i][0] = `${month}월 ${day}일 주`;
        }
    } else if (period === 'daily') {
        for (let i = 0; i < sortedList.length; i++) {
            let month = new Date(sortedList[i][0]).getMonth() + 1;
            let day = new Date(sortedList[i][0]).getDate();
            sortedList[i][0] = `${month}월 ${day}일`;
        }
    };

    if (period == 'daily') {
        sortedList.unshift(['Day', 'Pageviews']);
        document.getElementById('todayPageView').innerText = `오늘의 페이지뷰 수: ${sortedList[sortedList.length-1][1]}`;
        sortedList[sortedList.length-1][0] = '오늘';
    } else if (period == 'weekly') {
        sortedList.unshift(['Week', 'Pageviews']);
    } else if (period == 'monthly') {
        sortedList.unshift(['Month', 'Pageviews']);
    };

    drawChart(sortedList, period);
}

// Query the API and print the results to the page.
function queryReports(period, startDate, endDate) {
    gapi.client.request({
        path: '/v4/reports:batchGet',
        root: 'https://analyticsreporting.googleapis.com/',
        method: 'POST',
        body: {
            reportRequests: [
                {
                    viewId: viewId,
                    dateRanges: [
                        {   startDate: startDate, endDate: endDate }
                    ],
                    metrics: [
                        {   expression: 'ga:pageviews'  },
                    ]
                }
            ]
        }
    }).then(function(response) {
        collectData(period, startDate, endDate, response);
    }).catch(error => console.log(error));
}

function collectData(period, startDate, endDate, response) {
    let actualData;

    if (response.result.reports[0].data.rows) {
        actualData = response.result.reports[0].data.rows[0].metrics[0].values[0];
    } else {
        actualData = response.result.reports[0].data.totals[0].values[0];
    };

    let date;

    if (period === 'monthly' || period === 'daily') {
        date = endDate;
    } else if (period === 'weekly') {
        date = startDate;
    };

    res.push([date, parseInt(actualData)]);
};

function drawChart(list, period) {

    // Create the data table.
    let data = new google.visualization.arrayToDataTable(list);
    let title;

    switch (period) {
        case 'daily' :
            title = '일주일 간';
            break;
        case 'weekly':
            title = '한달 간';
            break;
        case 'monthly':
            title = '6개월 간';
            break;
        default:
            console.log('그래프를 그리는 데 문제가 생겼습니다.');
    }

    // Set chart options
    let options = {
        'title': `최근 ${title} 페이지뷰 변화`,
        'width': '550px',
        'height': '300px',
        'legend': 'none',
        'titlePosition': 'out',
    };

    // Instantiate and draw our chart, passing in some options.
    let chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}



function fetchingMonthlyGAData() {
    calculateMonthlyDateAndFetchingData(0);
    calculateMonthlyDateAndFetchingData(-1);
    calculateMonthlyDateAndFetchingData(-2);
    calculateMonthlyDateAndFetchingData(-3);
    calculateMonthlyDateAndFetchingData(-4);
    calculateMonthlyDateAndFetchingData(-5);

    setTimeout(function() {
        sortList('monthly', res);
    },1500)
};

function calculateMonthlyDateAndFetchingData(period) {
    let date = new Date();
    let curMonth, curDate;

    let curYear = date.getFullYear();
    let originalCurMonth = date.getMonth() + 1;
    if (originalCurMonth.toString().length === 1) {
        curMonth = '0' + originalCurMonth.toString();
    }

    let originalCcrDate = date.getDate();
    if (originalCcrDate.toString().length === 1) {
        curDate = '0' + originalCcrDate.toString();
    }

    let lastDayOfMonth;

    let startDate, endDate;

    if (period === 0) {
        lastDayOfMonth = new Date(curYear, originalCurMonth, 0);
        startDate = `${curYear}-${curMonth}-01`;
        endDate = `${curYear}-${curMonth}-${lastDayOfMonth.getDate()}`;
    } else {
        curMonth = parseInt(curMonth) + period;

        if (curMonth <= 0) {
            curMonth = curMonth + 12;
            curYear = curYear - 1;
        }

        lastDayOfMonth = new Date(curYear, curMonth, 0).getDate();

        if (curMonth.toString().length === 1) {
            curMonth = '0' + curMonth.toString();
        };
        if (lastDayOfMonth.toString().length === 1) {
            lastDayOfMonth = '0' + lastDayOfMonth.toString();
        }

        startDate = `${curYear}-${curMonth}-01`;
        endDate = `${curYear}-${curMonth}-${lastDayOfMonth}`;
    };

    queryReports('monthly', startDate, endDate);
}

function fetchingWeeklyGAData() {
    calculateWeeklyDateAndFetchingData(0);
    calculateWeeklyDateAndFetchingData(-1);
    calculateWeeklyDateAndFetchingData(-2);
    calculateWeeklyDateAndFetchingData(-3);
    calculateWeeklyDateAndFetchingData(-4);

    setTimeout(function() {
        sortList('weekly', res);
    },1500)
};

function calculateWeeklyDateAndFetchingData(period) {
    let today = new Date();
    let startDate = today;
    let endDate = today;
    let startYear, startMonth, realstartDate, endYear, endMonth, realEndDate;

    let oneDay = 1000 * 60 * 60 * 24 * 1;

    for (let i = 0; i < 7; i++) {
        if (startDate.getDay() !== 1) {
            startDate = new Date(startDate.getTime() - oneDay);
        }
    };

    for (let i = 0; i < 7; i++) {
        if (endDate.getDay() !== 0) {
            endDate = new Date(endDate.getTime() + oneDay);
        }
    };

    let oneWeek = 1000 * 60 * 60 * 24 * 7 * period;

    startDate = new Date(startDate.getTime() + oneWeek);
    endDate = new Date(endDate.getTime() + oneWeek);

    startYear = startDate.getFullYear();
    startMonth = startDate.getMonth() + 1;
    realstartDate = startDate.getDate();

    endYear = endDate.getFullYear();
    endMonth = endDate.getMonth() + 1;
    realEndDate = endDate.getDate();

    if (startMonth.toString().length === 1) {
        startMonth = '0' + startMonth.toString();
    };
    if (realstartDate.toString().length === 1) {
        realstartDate = '0' + realstartDate.toString();
    }

    if (endMonth.toString().length === 1) {
        endMonth = '0' + endMonth.toString();
    };
    if (realEndDate.toString().length === 1) {
        realEndDate = '0' + realEndDate.toString();
    }

    startDate = `${startYear}-${startMonth}-${realstartDate}`;
    endDate = `${endYear}-${endMonth}-${realEndDate}`;

    queryReports('weekly', startDate, endDate);
}

