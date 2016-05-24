(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TaskDetailController', TaskDetailController);

    TaskDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Task'];

    function TaskDetailController($scope, $rootScope, $stateParams, entity, Task) {
        var vm = this;
        vm.task = entity;
        vm.load = function (id) {
            Task.get({id: id}, function(result) {
                vm.task = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:taskUpdate', function(event, result) {
            vm.task = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
