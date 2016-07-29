(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_partial_construction_servicesDetailController', Freecom_partial_construction_servicesDetailController);

    Freecom_partial_construction_servicesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_partial_construction_services', 'Free_cfdi', 'C_federal_entity'];

    function Freecom_partial_construction_servicesDetailController($scope, $rootScope, $stateParams, entity, Freecom_partial_construction_services, Free_cfdi, C_federal_entity) {
        var vm = this;
        vm.freecom_partial_construction_services = entity;
        vm.load = function (id) {
            Freecom_partial_construction_services.get({id: id}, function(result) {
                vm.freecom_partial_construction_services = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_partial_construction_servicesUpdate', function(event, result) {
            vm.freecom_partial_construction_services = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
