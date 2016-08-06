(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_concept_fuelDetailController', Freecom_concept_fuelDetailController);

    Freecom_concept_fuelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_concept_fuel', 'Freecom_fuel_consumption'];

    function Freecom_concept_fuelDetailController($scope, $rootScope, $stateParams, entity, Freecom_concept_fuel, Freecom_fuel_consumption) {
        var vm = this;
        vm.freecom_concept_fuel = entity;
        vm.load = function (id) {
            Freecom_concept_fuel.get({id: id}, function(result) {
                vm.freecom_concept_fuel = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_concept_fuelUpdate', function(event, result) {
            vm.freecom_concept_fuel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
