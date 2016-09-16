(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Transactions_historyDeleteController',Transactions_historyDeleteController);

    Transactions_historyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Transactions_history'];

    function Transactions_historyDeleteController($uibModalInstance, entity, Transactions_history) {
        var vm = this;

        vm.transactions_history = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Transactions_history.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
