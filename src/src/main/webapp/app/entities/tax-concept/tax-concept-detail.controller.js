(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Tax_conceptDetailController', Tax_conceptDetailController);

    Tax_conceptDetailController.$inject = ['entity', '$uibModalInstance'];

    function Tax_conceptDetailController(entity, $uibModalInstance) {
        var vm = this;
        vm.tax_concept = entity;

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
