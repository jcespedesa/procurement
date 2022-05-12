
function reportChart(dataHMIS,dataBedList,dataHmax) 
{
    //var htmldata=${data};
    
    var dataHMIS=dataHMIS;
    var dataBedList=dataBedList;
    var dataHmax=dataHmax;
                               
    var ctx=document.getElementById('myChart').getContext('2d');
    var myChart=new Chart(ctx, {
        type: 'line',
            data: {
                labels: Object.keys(dataHMIS),
                datasets: [{
                    data: Object.keys(dataHMIS).map(function(key) {return dataHMIS[key];}),
                    borderColor: "red",
                    fill: false,
                    borderWidth: 1,
                    label: "HMIS Data"
                    
                },{
               
                    data: Object.keys(dataBedList).map(function(key) {return dataBedList[key];}),
                    borderColor: "green",
                    fill: false,
                    borderWidth: 1,
                    label: "Bed List Data"
                   
                },{
                    
                    data: Object.keys(dataHmax).map(function(key) {return dataHmax[key];}),
                    borderColor: "blue",
                    fill: false,
                    borderWidth: 1,
                    label: "Max Hourly Census"
                    
                }]
            },
            options: 
            {
            	scales: 
                {
                    y: 
                    {
                        beginAtZero: true
                    },
            
            		xAxes: [{
            			ticks: {
            				autoSkip: false,
            				maxRotation: 55,
            				minRotation: 55
            			}
            		}]
                }
            }
    });
}