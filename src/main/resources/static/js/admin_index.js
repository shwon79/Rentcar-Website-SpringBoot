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

function displayDailyGraph() {
    res = [];

    fetchingDailyGAData();
};

function fetchingDailyGAData() {
    queryReports('daily', '7daysAgo', '6daysAgo');
    queryReports('daily', '6daysAgo', '5daysAgo');
    queryReports('daily', '5daysAgo', '4daysAgo');
    queryReports('daily', '4daysAgo', '3daysAgo');
    queryReports('daily', '3daysAgo', '2daysAgo');
    queryReports('daily', '2daysAgo', '1daysAgo');
    queryReports('daily', '1daysAgo', 'today');

    setTimeout(function() {
        sortList('daily', res);
    },1800)
};

// function calculateAndCallQueryReports(period, startDate, endDate) {
//     let date = new Date();
//
//
// }

function sortList(period, res) {
    let sortedList = res.sort(function(a,b) {
        return a[0] - b[0];
    }).reverse();

    if (period === 'monthly') {
        for (let i = 0; i < sortedList.length; i++) {
            sortedList[i][0] = sortedList[i][0]/30 + '개월 전';
        }
    } else {
        for (let i = 0; i < sortedList.length; i++) {
            sortedList[i][0] = sortedList[i][0] + '일 전';
        }
    };

    if (period == 'daily') {
        sortedList.unshift(['Day', 'Pageviews']);
        document.getElementById('todayPageView').innerText = `오늘의 페이지뷰 수: ${sortedList[sortedList.length-1][1]}`;
    } else if (period == 'weekly') {
        sortedList.unshift(['Week', 'Pageviews']);
    } else if (period == 'monthly') {
        sortedList.unshift(['Month', 'Pageviews']);
    };

    sortedList[sortedList.length-1][0] = '오늘';

    console.log(sortedList);
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
        collectData(period, endDate, response);
    }).catch(error => console.log(error));
}

function collectData(period, endDate, response) {
    let actualData;

    if (response.result.reports[0].data.rows) {
        actualData = response.result.reports[0].data.rows[0].metrics[0].values[0];
    } else {
        actualData = response.result.reports[0].data.totals[0].values[0];
    };

    console.log(actualData);

    let date;

    if (endDate === 'today') {
        date = 0;
    } else {
        date = parseInt(endDate);
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
        'width': 500,
        'height': 300,
        'legend': 'none',
    };

    // Instantiate and draw our chart, passing in some options.
    let chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}

function displayWeeklyGraph() {
    res = [];

    fetchingWeeklyGAData();
};

function displayMonthlyGraph() {
    res = [];

    fetchingMonthlyGAData();
};

function fetchingMonthlyGAData() {
    queryReports('monthly', '179daysAgo', '150daysAgo');
    queryReports('monthly', '149daysAgo', '120daysAgo');
    queryReports('monthly', '119daysAgo', '90daysAgo');
    queryReports('monthly', '89daysAgo', '60daysAgo');
    queryReports('monthly', '59daysAgo', '30daysAgo');
    queryReports('monthly', '29daysAgo', 'today');

    setTimeout(function() {
        sortList('monthly', res);
    },1500)
}

function fetchingWeeklyGAData() {
    queryReports('weekly', '34daysAgo', '28daysAgo');
    queryReports('weekly', '27daysAgo', '21daysAgo');
    queryReports('weekly', '20daysAgo', '14daysAgo');
    queryReports('weekly', '13daysAgo', '7daysAgo');
    queryReports('weekly', '6daysAgo', 'today');

    setTimeout(function() {
        sortList('weekly', res);
    },1500)
};