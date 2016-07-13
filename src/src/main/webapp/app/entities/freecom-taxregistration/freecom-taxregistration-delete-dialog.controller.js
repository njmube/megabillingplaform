(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxregistrationDeleteController',Freecom_taxregistrationDeleteController);

    Freecom_taxregistrationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_taxregistration'];

    function Freecom_taxregistrationDeleteController($uibModalInstance, entity, Freecom_taxregistration) {
        var vm = this;
        vm.freecom_taxregistration = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_taxregistration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
