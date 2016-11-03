(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11_transferDeleteController',Com_ecc_11_transferDeleteController);

    Com_ecc_11_transferDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_ecc_11_transfer'];

    function Com_ecc_11_transferDeleteController($uibModalInstance, entity, Com_ecc_11_transfer) {
        var vm = this;
        vm.com_ecc_11_transfer = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_ecc_11_transfer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
