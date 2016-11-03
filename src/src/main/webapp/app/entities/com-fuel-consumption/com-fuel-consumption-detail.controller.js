(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_fuel_consumptionDetailController', Com_fuel_consumptionDetailController);

    Com_fuel_consumptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_fuel_consumption', 'Cfdi'];

    function Com_fuel_consumptionDetailController($scope, $rootScope, $stateParams, entity, Com_fuel_consumption, Cfdi) {
        var vm = this;
        vm.com_fuel_consumption = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_fuel_consumptionUpdate', function(event, result) {
            vm.com_fuel_consumption = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
