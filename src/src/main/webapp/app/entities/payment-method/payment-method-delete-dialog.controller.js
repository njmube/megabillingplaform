(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Payment_methodDeleteController',Payment_methodDeleteController);

    Payment_methodDeleteController.$inject = ['$uibModalInstance', 'entity', 'Payment_method'];

    function Payment_methodDeleteController($uibModalInstance, entity, Payment_method) {
        var vm = this;

        vm.payment_method = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Payment_method.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
