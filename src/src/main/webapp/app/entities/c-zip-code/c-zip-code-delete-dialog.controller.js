(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('C_zip_codeDeleteController',C_zip_codeDeleteController);

    C_zip_codeDeleteController.$inject = ['$uibModalInstance', 'entity', 'C_zip_code'];

    function C_zip_codeDeleteController($uibModalInstance, entity, C_zip_code) {
        var vm = this;
        vm.c_zip_code = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            C_zip_code.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
