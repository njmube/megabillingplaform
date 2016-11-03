(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_foreign_exchangeDeleteController',Com_foreign_exchangeDeleteController);

    Com_foreign_exchangeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_foreign_exchange'];

    function Com_foreign_exchangeDeleteController($uibModalInstance, entity, Com_foreign_exchange) {
        var vm = this;
        vm.com_foreign_exchange = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_foreign_exchange.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
