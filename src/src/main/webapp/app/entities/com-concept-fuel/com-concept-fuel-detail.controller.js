(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_concept_fuelDetailController', Com_concept_fuelDetailController);

    Com_concept_fuelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_concept_fuel', 'Com_fuel_consumption'];

    function Com_concept_fuelDetailController($scope, $rootScope, $stateParams, entity, Com_concept_fuel, Com_fuel_consumption) {
        var vm = this;
        vm.com_concept_fuel = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_concept_fuelUpdate', function(event, result) {
            vm.com_concept_fuel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
