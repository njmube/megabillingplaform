(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Voucher_typeDeleteController',Voucher_typeDeleteController);

    Voucher_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Voucher_type'];

    function Voucher_typeDeleteController($uibModalInstance, entity, Voucher_type) {
        var vm = this;
        vm.voucher_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Voucher_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
