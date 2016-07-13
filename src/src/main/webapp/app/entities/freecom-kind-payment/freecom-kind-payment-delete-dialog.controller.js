(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_kind_paymentDeleteController',Freecom_kind_paymentDeleteController);

    Freecom_kind_paymentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_kind_payment'];

    function Freecom_kind_paymentDeleteController($uibModalInstance, entity, Freecom_kind_payment) {
        var vm = this;
        vm.freecom_kind_payment = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_kind_payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
