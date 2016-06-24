(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_moneyDeleteController',C_moneyDeleteController);

    C_moneyDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_money'];

    function C_moneyDeleteController($uibModalInstance, entity, C_money) {
        var vm = this;
        vm.c_money = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_money.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
