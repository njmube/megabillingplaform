(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Confirmation_delete_certController', Confirmation_delete_certController);

    Confirmation_delete_certController.$inject = ['$uibModalInstance'];

    function Confirmation_delete_certController ($uibModalInstance) {
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

