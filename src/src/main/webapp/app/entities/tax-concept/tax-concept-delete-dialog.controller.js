(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDeleteController',Tax_conceptDeleteController);

    Tax_conceptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tax_concept'];

    function Tax_conceptDeleteController($uibModalInstance, entity, Tax_concept) {
        var vm = this;
        vm.tax_concept = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tax_concept.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
