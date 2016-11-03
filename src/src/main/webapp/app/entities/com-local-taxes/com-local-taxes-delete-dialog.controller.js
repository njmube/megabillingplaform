(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_local_taxesDeleteController',Com_local_taxesDeleteController);

    Com_local_taxesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_local_taxes'];

    function Com_local_taxesDeleteController($uibModalInstance, entity, Com_local_taxes) {
        var vm = this;
        vm.com_local_taxes = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_local_taxes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
