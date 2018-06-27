
( function ( $ ) {
    var ctx = document.getElementById( "radarChart" );
    ctx.height = 160;
    var myChart = new Chart( ctx, {
        type: 'radar',
        data: {
            labels: jQuery(ctx).data('labels'),
            datasets: [
                {
                    label: "My First dataset",
                    data: jQuery(ctx).data('values'),
                    borderColor: "rgba(0, 123, 255, 0.6)",
                    borderWidth: "1",
                    backgroundColor: "rgba(0, 123, 255, 0.4)"
                }
            ]
        },
        options: {
            legend: {
                display: false
            },
            scale: {
                ticks: {
                    beginAtZero: true
                }
            }
        }
    } );
} )( jQuery );