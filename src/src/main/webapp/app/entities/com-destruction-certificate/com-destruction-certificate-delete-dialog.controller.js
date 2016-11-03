(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_destruction_certificateDeleteController',Com_destruction_certificateDeleteController);

    Com_destruction_certificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_destruction_certificate'];

    function Com_destruction_certificateDeleteController($uibModalInstance, entity, Com_destruction_certificate) {
        var vm = this;
        vm.com_destruction_certificate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_destruction_certificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
