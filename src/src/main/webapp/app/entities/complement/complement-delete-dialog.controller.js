(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ComplementDeleteController',ComplementDeleteController);

    ComplementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Complement'];

    function ComplementDeleteController($uibModalInstance, entity, Complement) {
        var vm = this;
        vm.complement = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Complement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
