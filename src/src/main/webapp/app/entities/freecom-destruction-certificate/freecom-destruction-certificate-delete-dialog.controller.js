(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_destruction_certificateDeleteController',Freecom_destruction_certificateDeleteController);

    Freecom_destruction_certificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_destruction_certificate'];

    function Freecom_destruction_certificateDeleteController($uibModalInstance, entity, Freecom_destruction_certificate) {
        var vm = this;
        vm.freecom_destruction_certificate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_destruction_certificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
