(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Acquired_titleDetailController', Acquired_titleDetailController);

    Acquired_titleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Acquired_title'];

    function Acquired_titleDetailController($scope, $rootScope, $stateParams, entity, Acquired_title) {
        var vm = this;
        vm.acquired_title = entity;
        vm.load = function (id) {
            Acquired_title.get({id: id}, function(result) {
                vm.acquired_title = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:acquired_titleUpdate', function(event, result) {
            vm.acquired_title = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
