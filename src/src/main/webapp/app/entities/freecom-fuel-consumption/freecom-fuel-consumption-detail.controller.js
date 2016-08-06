(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_fuel_consumptionDetailController', Freecom_fuel_consumptionDetailController);

    Freecom_fuel_consumptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_fuel_consumption', 'Free_cfdi'];

    function Freecom_fuel_consumptionDetailController($scope, $rootScope, $stateParams, entity, Freecom_fuel_consumption, Free_cfdi) {
        var vm = this;
        vm.freecom_fuel_consumption = entity;
        vm.load = function (id) {
            Freecom_fuel_consumption.get({id: id}, function(result) {
                vm.freecom_fuel_consumption = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_fuel_consumptionUpdate', function(event, result) {
            vm.freecom_fuel_consumption = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
