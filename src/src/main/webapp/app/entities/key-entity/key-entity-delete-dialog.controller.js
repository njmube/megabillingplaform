(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Key_entityDeleteController',Key_entityDeleteController);

    Key_entityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Key_entity'];

    function Key_entityDeleteController($uibModalInstance, entity, Key_entity) {
        var vm = this;
        vm.key_entity = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Key_entity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
