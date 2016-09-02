(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_certificateDeleteController',Taxpayer_certificateDeleteController);

    Taxpayer_certificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Taxpayer_certificate'];

    function Taxpayer_certificateDeleteController($uibModalInstance, entity, Taxpayer_certificate) {
        var vm = this;

        vm.taxpayer_certificate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Taxpayer_certificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
