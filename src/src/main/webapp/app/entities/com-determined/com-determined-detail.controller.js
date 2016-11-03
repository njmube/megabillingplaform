(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_determinedDetailController', Com_determinedDetailController);

    Com_determinedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_determined', 'Com_concept_fuel', 'Freecom_tax_type'];

    function Com_determinedDetailController($scope, $rootScope, $stateParams, entity, Com_determined, Com_concept_fuel, Freecom_tax_type) {
        var vm = this;
        vm.com_determined = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_determinedUpdate', function(event, result) {
            vm.com_determined = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
