(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_partial_construction_servicesDeleteController',Freecom_partial_construction_servicesDeleteController);

    Freecom_partial_construction_servicesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_partial_construction_services'];

    function Freecom_partial_construction_servicesDeleteController($uibModalInstance, entity, Freecom_partial_construction_services) {
        var vm = this;
        vm.freecom_partial_construction_services = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_partial_construction_services.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
