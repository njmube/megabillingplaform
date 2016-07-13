(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Key_entityDetailController', Key_entityDetailController);

    Key_entityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Key_entity'];

    function Key_entityDetailController($scope, $rootScope, $stateParams, entity, Key_entity) {
        var vm = this;
        vm.key_entity = entity;
        vm.load = function (id) {
            Key_entity.get({id: id}, function(result) {
                vm.key_entity = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:key_entityUpdate', function(event, result) {
            vm.key_entity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
