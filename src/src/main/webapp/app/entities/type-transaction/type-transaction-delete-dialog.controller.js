(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_transactionDeleteController',Type_transactionDeleteController);

    Type_transactionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Type_transaction'];

    function Type_transactionDeleteController($uibModalInstance, entity, Type_transaction) {
        var vm = this;

        vm.type_transaction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Type_transaction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
