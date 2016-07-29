(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_foreign_exchangeDeleteController',Freecom_foreign_exchangeDeleteController);

    Freecom_foreign_exchangeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_foreign_exchange'];

    function Freecom_foreign_exchangeDeleteController($uibModalInstance, entity, Freecom_foreign_exchange) {
        var vm = this;
        vm.freecom_foreign_exchange = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_foreign_exchange.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
