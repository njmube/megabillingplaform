(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_educational_institutionsDeleteController',Freecom_educational_institutionsDeleteController);

    Freecom_educational_institutionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_educational_institutions'];

    function Freecom_educational_institutionsDeleteController($uibModalInstance, entity, Freecom_educational_institutions) {
        var vm = this;
        vm.freecom_educational_institutions = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_educational_institutions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
