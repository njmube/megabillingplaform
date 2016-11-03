(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_partial_construction_servicesDeleteController',Com_partial_construction_servicesDeleteController);

    Com_partial_construction_servicesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_partial_construction_services'];

    function Com_partial_construction_servicesDeleteController($uibModalInstance, entity, Com_partial_construction_services) {
        var vm = this;
        vm.com_partial_construction_services = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_partial_construction_services.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
