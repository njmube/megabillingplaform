(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Digital_certificateDeleteController',Digital_certificateDeleteController);

    Digital_certificateDeleteController.$inject = ['$uibModalInstance', 'entity', 'Digital_certificate'];

    function Digital_certificateDeleteController($uibModalInstance, entity, Digital_certificate) {
        var vm = this;
        vm.digital_certificate = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Digital_certificate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
