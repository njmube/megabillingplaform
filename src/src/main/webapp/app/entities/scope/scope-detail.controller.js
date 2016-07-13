(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ScopeDetailController', ScopeDetailController);

    ScopeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Scope'];

    function ScopeDetailController($scope, $rootScope, $stateParams, entity, Scope) {
        var vm = this;
        vm.scope = entity;
        vm.load = function (id) {
            Scope.get({id: id}, function(result) {
                vm.scope = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:scopeUpdate', function(event, result) {
            vm.scope = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
