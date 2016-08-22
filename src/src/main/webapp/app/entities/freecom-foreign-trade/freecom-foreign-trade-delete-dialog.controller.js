(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_tradeDeleteController',Freecom_foreign_tradeDeleteController);

    Freecom_foreign_tradeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_foreign_trade'];

    function Freecom_foreign_tradeDeleteController($uibModalInstance, entity, Freecom_foreign_trade) {
        var vm = this;

        vm.freecom_foreign_trade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_foreign_trade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
