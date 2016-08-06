(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_determinedDetailController', Freecom_determinedDetailController);

    Freecom_determinedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_determined', 'Freecom_concept_fuel', 'Freecom_tax_type'];

    function Freecom_determinedDetailController($scope, $rootScope, $stateParams, entity, Freecom_determined, Freecom_concept_fuel, Freecom_tax_type) {
        var vm = this;
        vm.freecom_determined = entity;
        vm.load = function (id) {
            Freecom_determined.get({id: id}, function(result) {
                vm.freecom_determined = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_determinedUpdate', function(event, result) {
            vm.freecom_determined = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
