(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tourist_passengerDetailController', Freecom_foreign_tourist_passengerDetailController);

    Freecom_foreign_tourist_passengerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_foreign_tourist_passenger', 'C_transit_type', 'C_type_road', 'Free_cfdi'];

    function Freecom_foreign_tourist_passengerDetailController($scope, $rootScope, $stateParams, entity, Freecom_foreign_tourist_passenger, C_transit_type, C_type_road, Free_cfdi) {
        var vm = this;
        vm.freecom_foreign_tourist_passenger = entity;
        vm.load = function (id) {
            Freecom_foreign_tourist_passenger.get({id: id}, function(result) {
                vm.freecom_foreign_tourist_passenger = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_foreign_tourist_passengerUpdate', function(event, result) {
            vm.freecom_foreign_tourist_passenger = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
