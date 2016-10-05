(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Confirmation_transferController', Confirmation_transferController);

    Confirmation_transferController.$inject = ['$uibModalInstance'];

    function Confirmation_transferController ($uibModalInstance) {
        var vm = this;

        vm.accept = function () {
            vm.isSaving = true;
            $uibModalInstance.close({
                isSaving: vm.isSaving
            });
        };

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();


