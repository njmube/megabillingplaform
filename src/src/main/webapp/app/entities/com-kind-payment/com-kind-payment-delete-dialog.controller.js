(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_kind_paymentDeleteController',Com_kind_paymentDeleteController);

    Com_kind_paymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_kind_payment'];

    function Com_kind_paymentDeleteController($uibModalInstance, entity, Com_kind_payment) {
        var vm = this;
        vm.com_kind_payment = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_kind_payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
