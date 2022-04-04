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
    queryReports('7daysAgo', '6daysAgo');
    queryReports('6daysAgo', '5daysAgo');
    queryReports('5daysAgo', '4daysAgo');
    queryReports('4daysAgo', '3daysAgo');
    queryReports('3daysAgo', '2daysAgo');
    queryReports('2daysAgo', '1daysAgo');
    queryReports('1daysAgo', 'today');

    setTimeout(function() {
        sortList(res);
    },1500)
};

function sortList(res) {
    let sortedList = res.sort(function(a,b) {
        return a[0].localeCompare(b[0]);
    }).reverse();

    sortedList.unshift(['Day', 'Pageviews']);
    sortedList[7][0] = '오늘'
    console.log(sortedList);

    document.getElementById('todayPageView').innerText = `오늘의 페이지뷰 수: ${sortedList[7][1]}`;
    drawChart(sortedList);
}

// Query the API and print the results to the page.
function queryReports(startDate, endDate) {
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
        collectData(endDate, response)
    }).catch(error => console.log(error));
}

function collectData(endDate, response) {
    let actualData = response.result.reports[0].data.rows[0].metrics[0].values[0];
    // console.log([startDate, parseInt(actualData)]);
    let date;
    if(endDate == 'today') {
        date = '0'
    } else {
        date = endDate.charAt(0);
    }
    res.push([date+'일 전', parseInt(actualData)]);
};

function drawChart(list) {

    // Create the data table.
    let data = new google.visualization.arrayToDataTable(list);

    // Set chart options
    let options = {
        'title':'최근 일주일 페이지뷰 변화',
        'width': 500,
        'height': 300,
        'legend': 'none',
    };

    // Instantiate and draw our chart, passing in some options.
    let chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}

function displayWeeklyGraph() {
    console.log('do you want to see the weekly graph?');
    res = [];

    fetchingWeeklyGAData();

}

function displayMonthlyGraph() {
    console.log('do you want to see the monthly graph?');
}

function fetchingWeeklyGAData() {
    // queryReports('7daysAgo', '6daysAgo');
    // queryReports('6daysAgo', '5daysAgo');
    // queryReports('5daysAgo', '4daysAgo');
    // queryReports('4daysAgo', '3daysAgo');
    // queryReports('3daysAgo', '2daysAgo');
    // queryReports('2daysAgo', '1daysAgo');
    // queryReports('1daysAgo', 'today');
    //
    // setTimeout(function() {
    //     sortList(res);
    // },1500)
};