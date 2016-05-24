(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_stateDeleteController',Voucher_stateDeleteController);

    Voucher_stateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Voucher_state'];

    function Voucher_stateDeleteController($uibModalInstance, entity, Voucher_state) {
        var vm = this;
        vm.voucher_state = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Voucher_state.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
