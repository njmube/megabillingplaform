(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Type_stateDetailController', Type_stateDetailController);

    Type_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Type_state'];

    function Type_stateDetailController($scope, $rootScope, $stateParams, entity, Type_state) {
        var vm = this;
        vm.type_state = entity;
        vm.load = function (id) {
            Type_state.get({id: id}, function(result) {
                vm.type_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:type_stateUpdate', function(event, result) {
            vm.type_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
