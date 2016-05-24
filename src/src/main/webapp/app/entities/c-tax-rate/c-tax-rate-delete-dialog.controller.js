(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_tax_rateDeleteController',C_tax_rateDeleteController);

    C_tax_rateDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_tax_rate'];

    function C_tax_rateDeleteController($uibModalInstance, entity, C_tax_rate) {
        var vm = this;
        vm.c_tax_rate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_tax_rate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
