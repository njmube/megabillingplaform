(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_partial_construction_servicesDetailController', Freecom_partial_construction_servicesDetailController);

    Freecom_partial_construction_servicesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_partial_construction_services', 'Free_cfdi', 'C_federal_entity', 'C_municipality', 'C_colony', 'C_zip_code'];

    function Freecom_partial_construction_servicesDetailController($scope, $rootScope, $stateParams, entity, Freecom_partial_construction_services, Free_cfdi, C_federal_entity, C_municipality, C_colony, C_zip_code) {
        var vm = this;

        vm.freecom_partial_construction_services = entity;

        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_partial_construction_servicesUpdate', function(event, result) {
            vm.freecom_partial_construction_services = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
