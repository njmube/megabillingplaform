(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_cfdiNotFreeEmitterCertificateDialogController', Free_cfdiNotFreeEmitterCertificateDialogController);

    Free_cfdiNotFreeEmitterCertificateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance'];

    function Free_cfdiNotFreeEmitterCertificateDialogController ($scope, $stateParams, $uibModalInstance) {
        var vm = this;

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
