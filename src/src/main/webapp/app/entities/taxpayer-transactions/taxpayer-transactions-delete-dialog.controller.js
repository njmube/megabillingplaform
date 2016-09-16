(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_transactionsDeleteController',Taxpayer_transactionsDeleteController);

    Taxpayer_transactionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_transactions'];

    function Taxpayer_transactionsDeleteController($uibModalInstance, entity, Taxpayer_transactions) {
        var vm = this;

        vm.taxpayer_transactions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_transactions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
