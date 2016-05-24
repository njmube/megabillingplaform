(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_regimeDeleteController',Tax_regimeDeleteController);

    Tax_regimeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_regime'];

    function Tax_regimeDeleteController($uibModalInstance, entity, Tax_regime) {
        var vm = this;
        vm.tax_regime = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tax_regime.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
