(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_digital_certificateDeleteController',Free_digital_certificateDeleteController);

    Free_digital_certificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Free_digital_certificate'];

    function Free_digital_certificateDeleteController($uibModalInstance, entity, Free_digital_certificate) {
        var vm = this;
        vm.free_digital_certificate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Free_digital_certificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
