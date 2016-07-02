(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-emitter', {
            parent: 'entity',
            url: '/free-emitter?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_emitter.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-emitter/free-emitters.html',
                    controller: 'Free_emitterController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_emitter');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
		.state('free-emitter.new', {
			parent: 'free-emitter',
			url: '/{login}/new',
			data: {
				authorities: ['ROLE_USER'],
				pageTitle: 'megabillingplatformApp.free_emitter.home.createLabel'
			},
			views: {
				'content@': {
					templateUrl: 'app/entities/free-emitter/free-emitter-new.html',
					controller: 'Free_emitterNewController',
					controllerAs: 'vm'
				}
			},
			resolve: {
				/*FreeEmitterEntity: [function () {
					return {
						reference: null,
						num_int: null,
						num_ext: null,
						street: null,
						create_date: null,
						activated: false,
						id: null
					};
				}],*/
				entity: ['$stateParams', 'Free_emitter', function($stateParams, Free_emitter) {
                            return Free_emitter.get({login : $stateParams.login});
                        }],
				user: ['$stateParams', 'User',  function($stateParams, User) {
					return User.get({login : $stateParams.login});
                }],
				translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
					$translatePartialLoader.addPart('free_emitter');
					$translatePartialLoader.addPart('global');
					return $translate.refresh();
				}]
			}
		})
        /*.state('free-emitter.new', {
            parent: 'free-emitter',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-emitter/free-emitter-dialog.html',
                    controller: 'Free_emitterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reference: null,
                                num_int: null,
                                num_ext: null,
                                street: null,
                                create_date: null,
                                activated: false,
                                rfc: null,
                                social_reason: null,
                                email: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-emitter', null, { reload: true });
                }, function() {
                    $state.go('free-emitter');
                });
            }]
        })*/
        .state('free-emitter.edit', {
            parent: 'free-emitter',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-emitter/free-emitter-dialog.html',
                    controller: 'Free_emitterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_emitter', function(Free_emitter) {
                            return Free_emitter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-emitter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-emitter.delete', {
            parent: 'free-emitter',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-emitter/free-emitter-delete-dialog.html',
                    controller: 'Free_emitterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_emitter', function(Free_emitter) {
                            return Free_emitter.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-emitter', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
