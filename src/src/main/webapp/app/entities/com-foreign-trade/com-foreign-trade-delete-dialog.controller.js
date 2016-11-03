(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_tradeDeleteController',Com_foreign_tradeDeleteController);

    Com_foreign_tradeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_foreign_trade'];

    function Com_foreign_tradeDeleteController($uibModalInstance, entity, Com_foreign_trade) {
        var vm = this;
        vm.com_foreign_trade = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_foreign_trade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
