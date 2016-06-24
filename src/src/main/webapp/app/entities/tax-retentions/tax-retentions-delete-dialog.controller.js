(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_retentionsDeleteController',Tax_retentionsDeleteController);

    Tax_retentionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_retentions'];

    function Tax_retentionsDeleteController($uibModalInstance, entity, Tax_retentions) {
        var vm = this;
        vm.tax_retentions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tax_retentions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
