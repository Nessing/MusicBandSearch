$(document).ready(function(){
    if($('.contacts').length) {
        ymaps.ready(init);
    function init() {
        var myMap = new ymaps.Map("map", {
            center: [31.219802, -100.588083],
            zoom: 5.4,
            scrollwheel: false,
            controls: ['fullscreenControl', 'zoomControl']
        }),

        myGeoObject = new ymaps.GeoObject({}, {
            preset: 'islands#blackStretchyIcon',
            draggable: false,
        }),
        myPieChart = new ymaps.Placemark();
        myMap.behaviors.disable('scrollZoom');  

        myMap.geoObjects
            .add(myGeoObject)
            .add(myPieChart)
            .add(new ymaps.Placemark([37.727448, -122.450876], {
                balloonContent: ''
            },  {
                    preset: 'islands#blueCircleDotIconWithCaption',
                    iconColor: '#3e84da',
                    iconPieChartCoreFillStyle: '#3e84da'
                }))
            .add(new ymaps.Placemark([30.444908, -97.765829], {
                balloonContent: ''
            },  {
                    preset: 'islands#blueCircleDotIconWithCaption',
                    iconColor: '#3e84da'
            }))
            .add(new ymaps.Placemark([37.229535, -77.403439], {
                balloonContent: ''
            },  {
                preset: 'islands#blueCircleDotIconWithCaption',
                iconColor: '#3e84da',
                iconPieChartCoreFillStyle: '#3e84da'
            }))
    }

    $('.burger').on('click', function(){
        $('.burger').toggleClass('open');
        $('.nav').toggleClass('open');
        $('.logo').toggleClass('close');
        $('section').toggleClass('open');
    })
}})