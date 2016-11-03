(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_partial_construction_servicesDetailController', Com_partial_construction_servicesDetailController);

    Com_partial_construction_servicesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_partial_construction_services', 'Cfdi', 'C_federal_entity', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Com_partial_construction_servicesDetailController($scope, $rootScope, $stateParams, entity, Com_partial_construction_services, Cfdi, C_federal_entity, C_municipality, C_colony, C_zip_code) {
        var vm = this;
        vm.com_partial_construction_services = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_partial_construction_servicesUpdate', function(event, result) {
            vm.com_partial_construction_services = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
